package com.ojicoin.cookiepang.service

import com.klaytn.caver.abi.datatypes.Type
import com.klaytn.caver.contract.Contract
import org.springframework.stereotype.Service

/**
 * @author seongchan.kang
 */
@Service
class CoinContractService(
    private val coinContract: Contract
) {

    enum class CoinContractMethod(val methodName: String) {
        IS_MAX_APPROVED_ADDRESS("maxApprovedAddress");
    }

    // FIXME: 커스텀 익셉션 추가
    fun isMaxApprovedAddress(address: String): Boolean {
        return try {
            val callResult: List<Type<*>> = coinContract.call(CoinContractMethod.IS_MAX_APPROVED_ADDRESS.methodName, address)
            val result: Type<Boolean> = callResult[0] as Type<Boolean>
            result.value
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException()
        }
    }
}
