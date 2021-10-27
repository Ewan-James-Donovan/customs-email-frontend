/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.customs.emailfrontend.controllers

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import uk.gov.hmrc.customs.emailfrontend.views.html.{accessibility_statement, start_page}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}

@Singleton
class ApplicationController @Inject()(view: start_page,
                                      accessibilityStatementView: accessibility_statement,
                                      mcc: MessagesControllerComponents)
                                     (implicit override val messagesApi: MessagesApi)
  extends FrontendController(mcc) with I18nSupport {

  def start: Action[AnyContent] = Action { implicit request =>
    Ok(view())
  }

  def accessibilityStatement: Action[AnyContent] = Action { implicit request =>
    Ok(accessibilityStatementView())
  }

}
