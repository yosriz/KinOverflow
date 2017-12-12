package kin.kinoverflow.network

import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.DataSnapshotMapper
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Flowable

// Example:
//    getKinPerQuestionMap()
//            .subscribe { kinPerQuestionMap ->
//                var kin = kinPerQuestionMap.get("3488664")
//                Log.d("berry", "kin for question 3488664 ${kin.toString()}")
//            }


fun getKinPerQuestionMap() : Flowable<Map<String, Long>> {
    val kinPerQuestionDb = FirebaseDatabase.getInstance().getReference("question_kin")
    return RxFirebaseDatabase.observeValueEvent(kinPerQuestionDb, DataSnapshotMapper.mapOf(Long::class.java))
}

fun getKinPerAnswerMap() : Flowable<Map<String, Long>> {
    val kinPerQuestionDb = FirebaseDatabase.getInstance().getReference("answer_kin")
    return RxFirebaseDatabase.observeValueEvent(kinPerQuestionDb, DataSnapshotMapper.mapOf(Long::class.java))
}


