package com.example.calculatorapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var tvInput : TextView?= null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput= findViewById(R.id.id_tv_input)

    }
    fun onDigit(view: View){
        lastNumeric=true
       tvInput?.append((view as Button).text)
        lastDot=false
    }
    fun onClear(view: View){
        tvInput?.text=""
    }
    fun onDecimalPoint(view: View){
        if (lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric=false
            lastDot= true
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){
                    prefix="-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    var splitValue = tvValue.split("-")
                    var firstNum=splitValue[0]
                    var secondNum = splitValue[1]

                    if(prefix.isNotEmpty()){
                        firstNum= prefix + firstNum
                    }
                    tvInput?.text =removeZeroAfterDot((firstNum.toDouble()-secondNum.toDouble()).toString())
                }
                else if (tvValue.contains("+")){
                    var splitValue = tvValue.split("+")
                    var firstNum=splitValue[0]
                    var secondNum = splitValue[1]

                    if(prefix.isNotEmpty()){
                        firstNum= prefix + firstNum
                    }
                    tvInput?.text =removeZeroAfterDot((firstNum.toDouble()+secondNum.toDouble()).toString())
                }else if (tvValue.contains("*")){
                    var splitValue = tvValue.split("*")
                    var firstNum=splitValue[0]
                    var secondNum = splitValue[1]

                    if(prefix.isNotEmpty()){
                        firstNum= prefix + firstNum
                    }
                    tvInput?.text =removeZeroAfterDot((firstNum.toDouble()*secondNum.toDouble()).toString())
                }else if (tvValue.contains("/")){
                    var splitValue = tvValue.split("/")
                    var firstNum=splitValue[0]
                    var secondNum = splitValue[1]

                    if(prefix.isNotEmpty()){
                        firstNum= prefix + firstNum
                    }
                    tvInput?.text =removeZeroAfterDot((firstNum.toDouble()/secondNum.toDouble()).toString())
                }

            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot( result: String) : String{
        var value =result
        if(result.contains(".0")){

           value =result.substring(0,result.length-2)
        }
        return value
    }

  //It ensures that operators can only be added after a numeric digit and that duplicate operators are not added consecutively.
    fun onOperator(view: View){
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false

            }
        }

    }

    //returns true if the input string value contains any of the specified mathematical operators (+, /, *, -) and false if it either starts with a minus sign or doesn't contain any of these operators.
    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("+") ||
                    value.contains("/") ||
                    value.contains("*") ||
                    value.contains("-")
        }
    }
}