package com.example.bancosaqueapp

data class OperacaoFinanceira(
    var saldoDepois:Double = 0.0,
    var saldoAntes:Double,
    var valorOperacao:Double,
    var tipoOperacao:String


)

class HistoricoOperacoes{
    val historico = mutableListOf<OperacaoFinanceira>()
}