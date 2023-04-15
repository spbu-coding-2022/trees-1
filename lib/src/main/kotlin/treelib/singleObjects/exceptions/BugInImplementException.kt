package treelib.singleObjects.exceptions

class BugInImplementException : Exception {
    constructor() : super()

    constructor(message: String) : super(
        "Error: $message",
    )

    constructor(message: String, cause: Throwable) : super(
        "Error: $message",
        cause,
    )

    constructor(cause: Throwable) : super(cause)
}
