package com.quicklybly.billscollector.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.quicklybly.billscollector.model.AdviceRequest
import io.netty.util.internal.ThreadLocalRandom
import mu.KotlinLogging
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class ChatService(
    private val model: OpenAiChatModel,
    private val objectMapper: ObjectMapper,
) {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun getAdvice(request: AdviceRequest): String {
        val prompt = getPrompt(request)

        return try {
            model.call(prompt)
        } catch (e: Exception) {
            logger.error("error occurred during ChatGPT call", e)
            getDefaultAdvice()
        }
    }

    private fun getPrompt(request: AdviceRequest): String {
        return PROMPT_START + objectMapper.writeValueAsString(request)
    }

    private fun getDefaultAdvice(): String {
        val index = ThreadLocalRandom.current().nextInt(0, defaultAdvices.size - 1)

        return defaultAdvices[index]
    }

    companion object {
        private val logger = KotlinLogging.logger { }

        private const val EXAMPLE_JSON: String =
            """{"name":"Вода","description":"Счет за воду","usages":[{"count_date":"2024-01-01","usage":"200"},{"count_date":"2024-02-01","usage":"500"},{"count_date":"2024-03-01","usage":"800"}]}"""

        private const val PROMPT_START =
            "Ты эксперт в области оптимизации потребления жилищно-коммунальных услуг,твоя задача проанализировать предоставленные данные и выдать совет по потреблению коммунальных услуг, который поможет клиенту снизить расходы без потери комфорта. Исходные данные приходят в формате json. Например: $EXAMPLE_JSON. Пример твоей работы: Ввод клиента: $EXAMPLE_JSON, Ответ: 'Ваше потребление резко возросло, возможно у вас протекает кран.' Теперь твоя очередь, пользовательский ввод:"

        private const val DEFAULT_ADVICE_PREFIX =
            "Для оптимизации потребления важно следовать базовым принципам бережного потребления, вот один из них:\n"
        private val defaultAdvices = listOf(
            "Аудит потребления: регулярно отслеживать и анализировать ваше потребление, чтобы выявлять аномалии и избыточное использование ресурсов.",
            "Улучшение энергоэффективности: использовать энергосберегающие и водосберегающие устройства и приборы, а также утеплять помещения для минимизации потребления энергии на отопление и охлаждение.",
            "Поведение потребителей: развивать привычки, например, выключать свет при покидании комнаты, использовать холодную воду для стирки, если это возможно, и принимать короткие души вместо полных ванн.",
            "Плановое обслуживание: регулярно обслуживать системы отопления, вентиляции и кондиционирования, а также сантехнику для предотвращения непредвиденных утечек и неэффективного использования.",
            "Инвестиции в обновление: при возможности модернизировать старое оборудование и инфраструктуру до более современных и экономичных вариантов.",
        ).map { DEFAULT_ADVICE_PREFIX + it }
    }
}
