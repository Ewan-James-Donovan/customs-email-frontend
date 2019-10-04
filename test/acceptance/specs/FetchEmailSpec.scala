/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package acceptance.specs

import acceptance.wiremockstub.{StubAuthClient, StubSave4Later, StubSubscriptionDisplay}
import common.pages.{StartPage, ThereIsAProblemWithTheServicePage}
import utils.SpecHelper

class ProblemWithTheServiceSpec
  extends AcceptanceTestSpec
    with SpecHelper
    with StubAuthClient
    with StubSave4Later
    with StubSubscriptionDisplay {

  feature("Problem with the service spec") {

    lazy val randomInternalId = generateRandomNumberString()
    lazy val randomEoriNumber = "GB" + generateRandomNumberString()

    scenario("User should see 'There is a problem with the service' when incorrect details are provided") {

      Given("user is on the 'Start' page")
      authenticate(randomInternalId, randomEoriNumber)
      save4LaterWithNoData(randomInternalId)
      navigateTo(StartPage)
      verifyCurrentPage(StartPage)
      stubSubscriptionDisplayBadRequestResponse(randomEoriNumber)

      When("the user clicks on 'Start now' button")
      clickOn(StartPage.emailLinkText)
      verifySubscriptionDisplayIsCalled(1, randomEoriNumber)

      Then("the user should be on 'There is a problem with the service' page")
      verifyCurrentPage(ThereIsAProblemWithTheServicePage)
    }

    scenario("User should see 'There is a problem with the service' when data could not be found") {

      Given("user is on the 'Start' page")
      authenticate(randomInternalId, randomEoriNumber)
      save4LaterWithNoData(randomInternalId)
      navigateTo(StartPage)
      verifyCurrentPage(StartPage)
      stubSubscriptionDisplayNotFoundResponse(randomEoriNumber)

      When("the user clicks on 'Start now' button")
      clickOn(StartPage.emailLinkText)
      verifySubscriptionDisplayIsCalled(1, randomEoriNumber)

      Then("the user should be on 'There is a problem with the service' page")
      verifyCurrentPage(ThereIsAProblemWithTheServicePage)
    }

    scenario("User should see 'There is a problem with the service' when there is an error in processing the request") {

      Given("user is on the 'Start' page")
      authenticate(randomInternalId, randomEoriNumber) 
      save4LaterWithNoData(randomInternalId)
      navigateTo(StartPage)
      verifyCurrentPage(StartPage)
      stubSubscriptionDisplayInternalServerResponse(randomEoriNumber)

      When("the user clicks on 'Start now' button")
      clickOn(StartPage.emailLinkText)
      verifySubscriptionDisplayIsCalled(1, randomEoriNumber)

      Then("the user should be on 'There is a problem with the service' page")
      verifyCurrentPage(ThereIsAProblemWithTheServicePage)
    }
  }
}
