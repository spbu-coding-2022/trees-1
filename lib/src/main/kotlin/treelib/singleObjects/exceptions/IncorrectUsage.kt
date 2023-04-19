package treelib.singleObjects.exceptions

class IncorrectUsage : Exception {
    constructor() : super(
        "Incorrect use of the tree"
    )

    constructor(message: String) : super(
        "$message",
    )

    constructor(message: String, cause: Throwable) : super(
        "$message",
        cause,
    )

    constructor(cause: Throwable) : super(cause)
}