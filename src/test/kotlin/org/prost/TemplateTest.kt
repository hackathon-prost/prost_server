package org.prost

import com.etermax.platform.template.core.action.Greet
import com.etermax.platform.template.core.domain.error.GreetingsNotFoundException
import com.etermax.platform.template.core.infrastructure.repository.InMemoryGreetings
import io.reactivex.Single
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

private const val EN_LANG = "en"
private const val ES_LANG = "es"

object TemplateTest : Spek({
    lateinit var greet: Greet
    lateinit var result: Single<String>
    lateinit var lang: String

    val inMemoryGreetings = InMemoryGreetings()
    greet = Greet(inMemoryGreetings)

    Feature("Hello Test") {
        Scenario("Greet with a valid language") {

            Given("English language") {
                lang = EN_LANG
            }

            When("Greet is called") {
                result = greet(lang)
            }

            Then("Returns `Hello, Platform`") {
                result.test().assertValue("Hello, Platform")
            }
        }

        Scenario("Greet with an invalid language") {

            Given("Non-English language") {
                lang = ES_LANG
            }

            When("Greet is called") {
                result = greet(lang)
            }

            Then("Throws GreetingsNotFoundException") {
                result.test().assertError { it is GreetingsNotFoundException }
            }

        }
    }
})