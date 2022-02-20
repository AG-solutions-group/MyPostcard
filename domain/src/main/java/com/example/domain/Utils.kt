package com.example.domain

import java.lang.Exception

sealed class Either<out L, out R> {
        /**
         * Represents the left side of [Either] class
         * which by convention is a "Failure".
         */
        data class Left<out L>(val a: L) : Either<L, Nothing>()

        /**
         * Represents the right side of [Either] class
         * which by convention is a "Success".
         */
        data class Right<out R>(val b: R) : Either<Nothing, R>()

        val isRight get() = this is Right<R>

        val isLeft get() = this is Left<L>

        fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
            when (this) {
                is Either.Left -> fnL(a)
                is Either.Right -> fnR(b)
            }

        //fun <T> flatMap(fn: (R) -> Either<L, T>): Either<L, T> {...}
        //fun <T> map(fn: (R) -> (T)): Either<L, T> {...}
    }

sealed class Failure() {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object ListNotAvailable : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}