/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.customs.emailfrontend.model

import play.api.libs.json._
import uk.gov.hmrc.customs.emailfrontend.utils.Utils

import java.time.{LocalDateTime, ZoneOffset}
import play.api.libs.ws.BodyWritable

case class UpdateEmail(eori: Eori, address: String, timestamp: LocalDateTime)

object UpdateEmail {

  val localDateTimeReads: Reads[LocalDateTime] = Reads[LocalDateTime](js =>
    js.validate[String].map[LocalDateTime](dtString => LocalDateTime.parse(dtString, Utils.dateFormatter))
  )

  private val localDateTimeWrites: Writes[LocalDateTime] =
    (d: LocalDateTime) => JsString(d.atZone(ZoneOffset.UTC).format(Utils.dateFormatter))

  private val eoriWrites: Writes[Eori] = (eori: Eori) => JsString(eori.id)

  val eoriReads: Reads[Eori] = Reads[Eori] { js =>
    js.validate[String].map[Eori](eori =>
      Eori(eori)
    )
  }

  implicit val formatEori: Format[Eori] = Format(eoriReads, eoriWrites)
  implicit val dateTimeJF: Format[LocalDateTime] = Format(localDateTimeReads, localDateTimeWrites)
  implicit val format: OFormat[UpdateEmail] = Json.format[UpdateEmail]

  implicit def jsonBodyWritable[T](implicit
                                   writes: Writes[T],
                                   jsValueBodyWritable: BodyWritable[JsValue]
                                  ): BodyWritable[T] = jsValueBodyWritable.map(writes.writes)
}
