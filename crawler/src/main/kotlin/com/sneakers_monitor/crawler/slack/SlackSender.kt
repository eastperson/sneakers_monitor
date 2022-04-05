package com.sneakers_monitor.crawler.slack

import com.slack.api.Slack
import com.slack.api.model.block.Blocks.asBlocks
import com.slack.api.model.block.Blocks.section
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.SectionBlock.SectionBlockBuilder
import com.slack.api.model.block.composition.BlockCompositions.markdownText
import com.slack.api.webhook.Payload.PayloadBuilder
import com.slack.api.webhook.WebhookPayloads.payload
import com.sneakers_monitor.crawler.domain.Brand
import com.sneakers_monitor.crawler.service.nike.ProductDto
import org.springframework.stereotype.Component
import java.io.IOException


@Component
class SlackSender(val slackBlockGenerator: SlackBlockGenerator){

    fun send(products:List<ProductDto>, brand: Brand) {
        try {
            Slack.getInstance().send(SlackChannel.RAFFLE.url, slackBlockGenerator.generateMessage(slackBlockGenerator.generateTitle(brand)))
        } catch (e:Exception) {
            println(e.message)
        }
        for (product in products) {
            val layoutBlocks = slackBlockGenerator.generateBlock(product)
            try {
                Slack.getInstance().send(SlackChannel.RAFFLE.url, slackBlockGenerator.generateMessage(layoutBlocks))
            } catch (ie: IOException) {
                println(ie.message)
            } catch (e:Exception) {
                Slack.getInstance().send(SlackChannel.RAFFLE.url, slackBlockGenerator.generateErrorMessage(product))
            }
        }
    }
}
