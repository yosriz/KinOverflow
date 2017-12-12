package kin.kinoverflow.network

import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.DataSnapshotMapper
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Flowable

// Example:
//    KinOverflowDb.getKinPerQuestionMap()
//            .subscribe { kinPerQuestionMap ->
//                var kin = kinPerQuestionMap.get("3488664")
//                Log.d("berry", "kin for question 3488664 ${kin.toString()}")
//            }

class KinOverflowDb {

    companion object Api {
        val KIN_PER_QUESTION_TABLE = "question_kin"
        val KIN_PER_ANSWER_TABLE = "answer_kin"

        fun getKinPerQuestionMap(): Flowable<Map<String, Long>> {
            return getKinMap(KIN_PER_QUESTION_TABLE)
        }

        fun getKinPerAnswerMap(): Flowable<Map<String, Long>> {
            return getKinMap(KIN_PER_ANSWER_TABLE)
        }

        private fun getKinMap(fromTable: String): Flowable<Map<String, Long>> {
            val kinDb = FirebaseDatabase.getInstance().getReference(fromTable)
            return RxFirebaseDatabase.observeValueEvent(kinDb, DataSnapshotMapper.mapOf(Long::class.java))
        }

        fun setKinToQuestion(questionId: String, kinValue: Long) {
            setKin(KIN_PER_QUESTION_TABLE, questionId, kinValue)
        }

        fun setKinToAnswer(answerId: String, kinValue: Long) {
            setKin(KIN_PER_ANSWER_TABLE, answerId, kinValue)
        }

        private fun setKin(table: String, id: String, kinValue: Long) {
            val kinDb = FirebaseDatabase.getInstance().getReference(table)
            kinDb.child(id).setValue(kinValue)
        }
    }
}