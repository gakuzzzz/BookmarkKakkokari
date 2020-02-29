package domain.exception

sealed trait BookmarkException extends RuntimeException

case object InvalidChracterUrlException extends BookmarkException
case object InvalidUrlException extends BookmarkException
