package com.vjezba.androidjetpacknews.ui.activities

import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vjezba.mvpcleanarhitecturefactorynews.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class MaratonacActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maratonac)
    }

    override fun onStart() {
        super.onStart()

        val maratonac1 = Maratonac("a", "a", 3.3, true)
        val maratonac2 = Maratonac("b", "b", 4.3, true)
        val maratonac3 = Maratonac("e", "e", 5.3, true)
        val maratonac4 = Maratonac("a", "a", 9.3, true)
        val maratonac5 = Maratonac("h", "h", 2.3, true)
        val maratonac6 = Maratonac("z", "z", 9.3, true)
        val maratonac7 = Maratonac("c", "c", 1.3, true)
        val maratonac8 = Maratonac("b", "b", 9.3, true)
        val maratonac9 = Maratonac("j", "j", 10.3, true)
        val maratonac10 = Maratonac("k", "k", 11.3, true)
        val maratonac11 = Maratonac("z", "z", 12.3, true)
        val maratonac12 = Maratonac("p", "p", 6.3, true)
        val maratonac13 = Maratonac("l", "l", 1.3, true)
        val maratonac14 = Maratonac("s", "s", 14.3, true)

        val listMaratonaca: MutableList<Maratonac> = mutableListOf()
        listMaratonaca.add(maratonac1)
        listMaratonaca.add(maratonac2)
        listMaratonaca.add(maratonac3)
        listMaratonaca.add(maratonac4)
        listMaratonaca.add(maratonac5)
        listMaratonaca.add(maratonac6)
        listMaratonaca.add(maratonac7)
        listMaratonaca.add(maratonac8)
        listMaratonaca.add(maratonac9)
        listMaratonaca.add(maratonac10)
        listMaratonaca.add(maratonac11)
        listMaratonaca.add(maratonac12)
        listMaratonaca.add(maratonac13)
        listMaratonaca.add(maratonac14)

        val first10ByAlphabet = listMaratonaca
            .sortedWith(compareByDescending<Maratonac> { it.bodovi }
                .thenBy { it.ime })

        val first10ByOrderAsOnBeggining = listMaratonaca.sortedByDescending { it.bodovi }.take(10)

        //Log.d("ISPIS JE", "Ispis je: ${first10ByOrderAsOnBeggining}")

        readTextFile()

    }

    fun readTextFile() {

        val textFileData = getFileTxtData()

        val correctTextFileData = textFileData.map {
            it.replace(",", "")  }

        val duplicatedWordList = mutableListOf<WordCounter>()

        for( textItem in correctTextFileData ) {

            var counterOfThisCurrentString = 0

            for( textItemSecond in correctTextFileData ) {

                if( textItemSecond.equals(textItem, ignoreCase = true) ) {
                    counterOfThisCurrentString++
                }
            }
            if ( counterOfThisCurrentString >= 2 ) {
                val newWord = WordCounter( textItem.toLowerCase(), counterOfThisCurrentString )
                duplicatedWordList.add(newWord)
                Log.d("ISPIS 444 JE", "Ispis NASLI SMO RIJEC KOJA SE NAJMANJE DVA PUT PONAVLJA: ${textItem}")
            }
        }

        for( textItem in duplicatedWordList.distinct() ) {
            Log.d("ISPIS", "Word: ${textItem.textData} (${textItem.counterText}) ")
        }

    }

    private fun getFileTxtData(): MutableList<String> {

        val textFileData = mutableListOf<String>()

        var string: String? = ""
        val stringBuilder = StringBuilder()
        val inputStream: InputStream = resources.openRawResource(
            resources.getIdentifier(
                "sample",
                "raw", packageName
            )
        )
        val reader = BufferedReader(InputStreamReader(inputStream))

        while (true) {
            try {
                if (reader.readLine().also { string = it } == null) break
            } catch (e: IOException) {
                e.printStackTrace()
            }
            stringBuilder.append(string).append("\n")

            textFileData.addAll(string?.split(" ") ?: listOf())

            Log.d("ISPIS JE", "Ispis je: ${textFileData}")
        }
        inputStream.close()

        return textFileData
    }


}