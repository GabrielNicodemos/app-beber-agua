package com.example.bebergua_lembrete.model

class CalcularIngestao {
    private var ML_JOVEM = 40.0
    private var ML_ADULTO = 35.0
    private var ML_IDOSO = 30.0
    private var ML_IDOSO_MAIS_66_ANOS = 25.0

    private var resultadoMl = 0.0
    private var resultadoTotalMl = 0.0

    fun CalcularMl(peso: Float, idade: Int) {
        if (idade <= 17) {
            resultadoMl = peso * ML_JOVEM
            resultadoTotalMl = resultadoMl
        } else if (idade <= 55) {
            resultadoMl = peso * ML_ADULTO
            resultadoTotalMl = resultadoMl
        } else if (idade <= 65) {
            resultadoMl = peso * ML_IDOSO
            resultadoTotalMl = resultadoMl
        } else {
            resultadoMl = peso * ML_IDOSO_MAIS_66_ANOS
            resultadoTotalMl = resultadoMl
        }
    }

    fun ResultMl(): Double{
        return resultadoTotalMl
    }
}