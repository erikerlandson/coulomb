/*
Copyright 2017 Erik Erlandson

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package coulomb

import shapeless._
import shapeless.syntax.singleton._
import singleton.ops._

package object infra {
  type True = Witness.`true`.T
  type False = Witness.`false`.T

  type XInt0 = Witness.`0`.T
  type XInt1 = Witness.`1`.T
  type XIntNeg1 = Witness.`-1`.T
}
