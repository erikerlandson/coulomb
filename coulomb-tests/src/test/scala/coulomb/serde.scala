/*
Copyright 2016-2019 Erik Erlandson

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

package coulomb.scalatest

object serde {
  def roundTripSerDe[T](v: T): T = {
    import java.io._

    class ObjectInputStreamWithCustomClassLoader(
      inputStream: InputStream) extends ObjectInputStream(inputStream) {
      override def resolveClass(desc: java.io.ObjectStreamClass): Class[_] = {
        try { Class.forName(desc.getName, false, getClass.getClassLoader) }
        catch { case ex: ClassNotFoundException => super.resolveClass(desc) }
      }
    }

    val bufout = new ByteArrayOutputStream()
    val obout = new ObjectOutputStream(bufout)

    obout.writeObject(v)

    val bufin = new ByteArrayInputStream(bufout.toByteArray)
    val obin = new ObjectInputStreamWithCustomClassLoader(bufin)

    obin.readObject().asInstanceOf[T]
  }
}