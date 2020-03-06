package com.example.bancosaqueapp

data class OperacaoFinanceira(
    var data:String,
    var saldoDepois:String,
    var saldoAntes:String,
    var valorOperacao:String,
    var tipoOperacao:String
)

class HistoricoOperacoes{
    val historico = mutableListOf<OperacaoFinanceira>()
}