package domain.models.user

import com.github.t3hnar.bcrypt._
import domain.error.{IllegalEncryptedPasswordError, UserError}

import scala.util.Success

final case class UserEncryptedPassword private (private val value: String) {
  def authenticate(password: UserPlainPassword): Boolean = {
    password.asString.isBcryptedSafe(value) match {
      case Success(true) => true
      case _             => false
    }
  }

  private[domain] def asString: String = value
}

object UserEncryptedPassword {
  private[domain] def apply(
    value: String
  ): Either[UserError, UserEncryptedPassword] =
    Either.cond(
      value.startsWith("""$2a$10"""),
      new UserEncryptedPassword(value),
      IllegalEncryptedPasswordError
    )
}
