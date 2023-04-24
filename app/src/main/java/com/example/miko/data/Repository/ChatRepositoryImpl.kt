import com.example.miko.data.mappers.toCompletions
import com.example.miko.data.remote.OpenApi
import com.example.miko.data.remote.PromptBody
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.repository.ChatRepository
import com.example.miko.domain.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val openApi: OpenApi
): ChatRepository {
    override suspend fun sendMessageData(
        model: String,
        role: String,
        content: String
    ): Resource<Completions> {
        return try {
            Resource.Success(
                openApi.getTextCompletion(
                    PromptBody(model, listOf(role, content))
                ).toCompletions()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown Error")
        }
    }


}