/**
 * Copyright 2011-2014 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.http

import com.ning.http.client.ProxyServer
import com.ning.http.client.ProxyServer.Protocol

import io.gatling.http.config.Proxy

package object ahc {

  val NoCredentials = (null, null)

  implicit class ProxyConverter(val proxy: Proxy) extends AnyVal {

    private def proxyServer(protocol: Protocol, port: Int): ProxyServer = {
      val (username, password) = proxy.credentials.map(c => (c.username, c.password)).getOrElse(NoCredentials)
      new ProxyServer(protocol, proxy.host, port, username, password)
    }

    def proxyServers: (ProxyServer, ProxyServer) = (proxyServer(Protocol.HTTP, proxy.port), proxyServer(Protocol.HTTPS, proxy.securePort))
  }
}
