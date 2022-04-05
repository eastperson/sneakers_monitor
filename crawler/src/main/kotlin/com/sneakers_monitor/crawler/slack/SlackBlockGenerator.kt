package com.sneakers_monitor.crawler.slack

import com.slack.api.model.block.Blocks.*
import com.slack.api.model.block.HeaderBlock.HeaderBlockBuilder
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.SectionBlock.SectionBlockBuilder
import com.slack.api.model.block.composition.BlockCompositions.markdownText
import com.slack.api.model.block.composition.BlockCompositions.plainText
import com.slack.api.model.block.element.BlockElements.*
import com.slack.api.model.block.element.ButtonElement.ButtonElementBuilder
import com.slack.api.model.block.element.ImageElement.ImageElementBuilder
import com.slack.api.webhook.Payload
import com.slack.api.webhook.Payload.PayloadBuilder
import com.slack.api.webhook.WebhookPayloads
import com.sneakers_monitor.crawler.domain.Brand
import com.sneakers_monitor.crawler.service.nike.ProductDto
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SlackBlockGenerator {

    fun generateTitle(brand:Brand):List<LayoutBlock> {
        val layoutBlocks:MutableList<LayoutBlock> = arrayListOf()
        val now = LocalDateTime.now()
        layoutBlocks.add(header { plainText:HeaderBlockBuilder -> plainText.text(plainText("${now.year}년 ${now.month.value}월 ${now.dayOfMonth}일 $brand 발매 정보"))})
        layoutBlocks.add(divider())
        return layoutBlocks
    }

    fun generateBlock(product: ProductDto):List<LayoutBlock> {
        val layoutBlocks:MutableList<LayoutBlock> = arrayListOf()
        layoutBlocks.add(section { section: SectionBlockBuilder ->
            section.text(
                markdownText(
                    """이름 : ${product.title}
                        |브랜드 : ${product.brand}
                        |출시일 : ${product.date}
                        |상품 색상 : ${product.color}
                        |상품 가격 : ${product.price}
                    """.trimMargin()
                )
            ).accessory(image { image: ImageElementBuilder ->
                image.imageUrl(
                    product.image
                ).altText("상품 사진")
            })
        })
        layoutBlocks.add(section { section: SectionBlockBuilder ->
            section.text(markdownText(" ")).accessory(
                button { button: ButtonElementBuilder ->
                    button.text(plainText("상품 보러가기")).url(product.url)
                }
            )
        })
        layoutBlocks.add(divider())
        return layoutBlocks
    }

    fun generateErrorMessage(product: ProductDto):Payload {
        return Payload.builder()
            .text("슬랙에 메시지를 출력하지 못했습니다.")
            .blocks(asBlocks(section { section: SectionBlockBuilder ->
                section.text(
                    markdownText(
                        "상품 아이디 : ${product.id}등록 에러 발생"))
            })).build()
    }

    fun generateMessage(layoutBlocks: List<LayoutBlock>):Payload {
        return Payload.builder()
            .text("슬랙에 메시지를 출력하지 못했습니다.")
            .blocks(layoutBlocks).build()
    }
}