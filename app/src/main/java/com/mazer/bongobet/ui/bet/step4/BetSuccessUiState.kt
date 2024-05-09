import com.mazer.bongobet.domain.entities.pojo.MatchHistoryResponse

interface BetSuccessUiState {

    object Initial: BetSuccessUiState
    object Loading: BetSuccessUiState
    object Complete: BetSuccessUiState
    data class GetLastMatchSuccess(val lastMatch: MatchHistoryResponse): BetSuccessUiState
    data class GetLastMatchError(val msg: String): BetSuccessUiState
}