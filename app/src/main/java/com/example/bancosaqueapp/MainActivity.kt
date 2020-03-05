package com.example.bancosaqueapp

//import sun.jvm.hotspot.utilities.IntArray
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var valorAtual = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDepositar?.setOnClickListener {
            if (transacaoMinima(currencyFormatDouble(txtDeposito.text.toString()))) {

                depositar(currencyFormatDouble(txtDeposito.text.toString()))
                updateSaldo()
                txtDeposito.setText("0")
            } else {
                aviso("Deposite um valor maior que R$10,00")
            }
        }

        btnSaqueDefinido?.setOnClickListener {
            if (transacaoMinima(currencyFormatDouble(txtSaque.text.toString()))) {
                debitar(currencyFormatDouble(txtSaque.text.toString()))
                updateSaldo()
                txtSaque.setText("0")
            } else {
                aviso("O valor mínimo para saque é de R$10,00")
            }
        }

        btnSaqueMax?.setOnClickListener {
            saque(valorAtual)
            updateSaldo()
            txtSaque.setText("0")
        }


        txtDeposito.addTextChangedListener( MoneyTextWatcher(txtDeposito))
        txtSaque.addTextChangedListener(MoneyTextWatcher(txtSaque))


    }

    fun currencyFormatDouble(currencyValue: String): Double {
        val cleanString = currencyValue.replace("[R$,.]".toRegex(), "").trim()
        val parsed = BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR)
            .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
        return parsed.toDouble()
    }

    private fun transacaoMinima(valor: Double): Boolean {
        return (valor >= 10.0)
    }

    private fun updateSaldo() {


        txtSaldo.text = formatSaldo(valorAtual).format()
    }

    private fun formatSaldo(saldo: Double): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt","BR")).format(saldo).toString()

        return currencyFormat
    }

    private fun aviso(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarEsconder() {
        ViewNotas.visibility = View.VISIBLE
    }

    private fun verificarSaldo(valor: Double): Boolean {
        return valor <= valorAtual
    }

    private fun depositar(deposito: Double) {
        valorAtual += deposito
    }

    private fun debitar(debito: Double) {
        valorAtual -= debito
    }

    private fun qtdNotas(valorSaque: Double, cifra: Int): Int {
        return (valorSaque / cifra).toInt()
    }

    private fun saqueParcial(valorSaque: Double, cifra: Int, nNotas: Int): Double {
        return valorSaque - (cifra * nNotas)
    }

    private fun saque(valor: Double) {
        var valorSaque = valor
        if (verificarSaldo(valorSaque)) {
            var NumeroNotasAtual:Int?

            //saqueParcial recebe os valores nNotas e cifra da nota com valor da nota anterior
            //1-pega o numero de notas
            NumeroNotasAtual = qtdNotas(valorSaque,100)
            //2-passa Nº de notas para a tela
            txtnNotas100.text = NumeroNotasAtual.toString()
            //3-redefine o valor saque
            valorSaque = saqueParcial(valorSaque,100, NumeroNotasAtual)

            NumeroNotasAtual = qtdNotas(valorSaque,50)
            txtnNotas50.text = NumeroNotasAtual.toString()
            valorSaque = saqueParcial(valorSaque,50, NumeroNotasAtual)

            NumeroNotasAtual = qtdNotas(valorSaque,20)
            txtnNotas20.text = NumeroNotasAtual.toString()
            valorSaque = saqueParcial(valorSaque,20, NumeroNotasAtual)

            NumeroNotasAtual = qtdNotas(valorSaque,10)
            txtnNotas10.text = NumeroNotasAtual.toString()
            valorSaque = saqueParcial(valorSaque,10, NumeroNotasAtual)

            NumeroNotasAtual = qtdNotas(valorSaque,5)
            txtnNotas5.text = NumeroNotasAtual.toString()
//7121
            debitar(valor)
            updateSaldo()
            mostrarEsconder()
        } else {
            aviso("Saldo Infuficiente para saque.")
        }
    }
}

class MoneyTextWatcher(editText: EditText) : TextWatcher {
    private val editTextWeakReference: WeakReference<EditText> = WeakReference<EditText>(editText)

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        val editText = editTextWeakReference.get() ?: return
        val s = editable.toString()
        if (s.isEmpty()) return
        editText.removeTextChangedListener(this)
        val cleanString = s.replace("[R$,.]".toRegex(), "").trim()
        val parsed = BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR)
            .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
        val formatted = NumberFormat.getCurrencyInstance(Locale("pt","BR")).format(parsed)
        editText.setText(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }
}
