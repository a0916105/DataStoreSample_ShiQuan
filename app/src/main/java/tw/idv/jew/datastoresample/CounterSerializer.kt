package tw.idv.jew.datastoresample

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import tw.idv.jew.datastoretest.Counter
import java.io.InputStream
import java.io.OutputStream

object CounterSerializer : Serializer<Counter> {
    override val defaultValue: Counter = Counter.getDefaultInstance()

    override fun readFrom(input: InputStream): Counter {
        try {
            return Counter.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: Counter, output: OutputStream) {
        t.writeTo(output)
    }
}