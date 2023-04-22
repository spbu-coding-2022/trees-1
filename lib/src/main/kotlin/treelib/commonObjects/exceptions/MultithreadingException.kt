package treelib.commonObjects.exceptions

open class MultithreadingException : Exception {
    constructor() : super(
        "Possible problem with multithreading",
    )

    constructor(message: String) : super(
        "Possible problem with multithreading -> $message",
    )

    constructor(message: String, cause: Throwable) : super(
        "Possible problem with multithreading -> $message",
        cause,
    )

    constructor(cause: Throwable) : super(cause)
}
