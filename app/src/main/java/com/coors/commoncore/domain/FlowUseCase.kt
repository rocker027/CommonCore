/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.coors.commoncore.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import com.coors.commoncore.result.Result

/**
 * @author gregho
 * @since 2020/8/12
 */
/**
 * Executes business logic in its execute method and keep posting updates to the result as [Result].
 * Handling an exception (emit [Result.Error] to the result) is the subclasses's responsibility.
 */
@ExperimentalCoroutinesApi
abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(parameters: P): Flow<Result<R>> = execute(parameters)
        .catch { e -> emit(Result.Error(Exception(e))) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<Result<R>>

    protected inline fun <T> resultFlow(crossinline apiSuspendFunc: suspend () -> Result<T>): Flow<T> =
        flow {
            when (val result = apiSuspendFunc()) {
                is Result.Success -> emit(result.data)
                is Result.Error -> throw result.exception
            }
        }

    @FlowPreview
    protected inline fun <T, R> Flow<T>.flatMapMergeResult(
        concurrency: Int? = null,
        crossinline transform: suspend (value: T) -> Result<R>
    ): Flow<R> {
        return if (concurrency == null) {
            flatMapMerge { value -> resultFlow { transform(value) } }
        } else {
            flatMapMerge(concurrency) { value -> resultFlow { transform(value) } }
        }
    }

    @FlowPreview
    protected inline fun <T, R> Flow<T>.flatMapConcatResult(crossinline transform: suspend (value: T) -> Result<R>): Flow<R> =
        flatMapConcat { value -> resultFlow { transform(value) } }


    @ExperimentalCoroutinesApi
    protected inline fun <T, R> Flow<T>.flatMapLatestResult(crossinline transform: suspend (value: T) -> Result<R>): Flow<R> =
        flatMapLatest { value -> resultFlow { transform(value) } }
}