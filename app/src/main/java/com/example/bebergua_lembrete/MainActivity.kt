package com.example.bebergua_lembrete

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.text.format.DateFormat
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.example.bebergua_lembrete.databinding.ActivityMainBinding
import com.example.bebergua_lembrete.model.CalcularIngestao
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var edit_peso: EditText
    private lateinit var edit_idade: EditText


    private lateinit var calcularIngestao: CalcularIngestao
    private var resultado = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendario: Calendar
    var horaAtual = 0
    var minutosAtual = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        iniciarComponentes()
        calcularIngestao = CalcularIngestao()

        binding.btnCalcular.setOnClickListener{
            if (binding.editPeso.text.toString().isEmpty()){
                edit_peso.error = "Informe seu peso"
                Toast.makeText(this, R.string.toast_informe_peso, Toast.LENGTH_SHORT).show()
            } else if (binding.editIdade.text.toString().isEmpty()){
                edit_idade.error = "Informe sua idade"
                Toast.makeText(this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show()
            } else if (binding.editPeso.text.toString().toFloat() < 0.0) {
                edit_peso.error = "Peso deve ser maior que 0"
                Toast.makeText(this, R.string.toast_valido_peso, Toast.LENGTH_SHORT).show()
            } else if (binding.editIdade.text.toString().toInt() < 0) {
                edit_idade.error = "Idade deve ser maior que 0"
                Toast.makeText(this, R.string.toast_valido_idade, Toast.LENGTH_SHORT).show()
            } else {
                val peso = binding.editPeso.text.toString().toFloat()
                val idade = binding.editIdade.text.toString().toInt()
                calcularIngestao.CalcularMl(peso, idade)
                resultado = calcularIngestao.ResultMl()
                var formatar = NumberFormat.getNumberInstance(Locale("pt", "br"))
                formatar.isGroupingUsed = false
                binding.txtResultadoMl.text = formatar.format(resultado) + " " + "ML"
            }
        }

        binding.icRefresh.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.alert_dialog_refresh)
                .setMessage(R.string.alert_dialog_desc)
                .setPositiveButton("Ok") { dialogInterface, i ->
                    binding.editPeso.setText("")
                    binding.editIdade.setText("")
                    binding.txtResultadoMl.setText("")
                }
            alertDialog.setNegativeButton("Cancelar") { dialogInterface, i ->
            }
            val dialog = alertDialog.create()
            dialog.show()
        }

        binding.btnLembrete.setOnClickListener{
            calendario = Calendar.getInstance()
            horaAtual  = calendario.get(Calendar.HOUR_OF_DAY)
            minutosAtual  = calendario.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(this, { timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                binding.txtHora.text = String.format("%02d", hourOfDay)
                binding.txtMinuto.text = String.format("%02d", minutes)
            }, horaAtual, minutosAtual, true)
            timePickerDialog.show()
        }

        binding.btnAlarme.setOnClickListener{

            if (!binding.txtHora.text.toString().isEmpty() && !binding.txtMinuto.text.toString().isEmpty()) {
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)

                intent.putExtra(AlarmClock.EXTRA_HOUR, binding.txtHora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, binding.txtMinuto.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.msg_alarme))
                startActivity(intent)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }

        }

    }

//    private fun iniciarComponentes() {
//        edit_peso = findViewById(R.id.edit_peso)
//        edit_idade = findViewById(R.id.edit_idade)
//        btn_calcular = findViewById(R.id.btn_calcular)
//        txt_resultado_ml = findViewById(R.id.txt_resultado_ml)
//        ic_refresh = findViewById(R.id.ic_refresh)
//        btn_lembrete = findViewById(R.id.btn_lembrete)
//        btn_alarme = findViewById(R.id.btn_alarme)
//        txt_horas = findViewById(R.id.txt_hora)
//        txt_minutos = findViewById(R.id.txt_minuto)
//    }
}