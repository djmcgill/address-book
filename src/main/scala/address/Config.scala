package address

import com.typesafe.config.ConfigFactory

object Config {
  private lazy val configFactory = ConfigFactory.load()
  lazy val addressBookFileName: String = configFactory.getString("addressBook.fileName")
}
