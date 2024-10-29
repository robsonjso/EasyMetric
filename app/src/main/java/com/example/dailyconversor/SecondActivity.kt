package com.example.dailyconversor // Substitua pelo pacote correto

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyconversor.R

class SecondActivity : AppCompatActivity() {

    // Declaração das variáveis
    private lateinit var inputFields: List<EditText>
    private lateinit var outputFields: List<TextView>
    private lateinit var inputSpinners: List<Spinner>
    private lateinit var outputSpinners: List<Spinner>
    private lateinit var convertButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        enableEdgeToEdge()

        // Inicializando as views
        initializeViews()

        // Preencher os spinners
        setupSpinners()

        // Configurando os cliques dos botões de conversão
        setupButtonListeners()
    }

    private fun initializeViews() {
        inputFields = listOf(
            findViewById(R.id.etInputLength),
            findViewById(R.id.etInputWeight),
            findViewById(R.id.etInputVolume),
            findViewById(R.id.etInputArea),
            findViewById(R.id.etInputTemperature),
            findViewById(R.id.etInputSpeed),
            findViewById(R.id.etInputTime)
        )

        outputFields = listOf(
            findViewById(R.id.tvOutputLength),
            findViewById(R.id.tvOutputWeight),
            findViewById(R.id.tvOutputVolume),
            findViewById(R.id.tvOutputArea),
            findViewById(R.id.tvOutputTemperature),
            findViewById(R.id.tvOutputSpeed),
            findViewById(R.id.tvOutputTime)
        )

        inputSpinners = listOf(
            findViewById(R.id.spinnerInputLengthUnit),
            findViewById(R.id.spinnerInputWeightUnit),
            findViewById(R.id.spinnerInputVolumeUnit),
            findViewById(R.id.spinnerInputAreaUnit),
            findViewById(R.id.spinnerInputTemperatureUnit),
            findViewById(R.id.spinnerInputSpeedUnit),
            findViewById(R.id.spinnerInputTimeUnit)
        )

        outputSpinners = listOf(
            findViewById(R.id.spinnerOutputLengthUnit),
            findViewById(R.id.spinnerOutputWeightUnit),
            findViewById(R.id.spinnerOutputVolumeUnit),
            findViewById(R.id.spinnerOutputAreaUnit),
            findViewById(R.id.spinnerOutputTemperatureUnit),
            findViewById(R.id.spinnerOutputSpeedUnit),
            findViewById(R.id.spinnerOutputTimeUnit)
        )

        convertButtons = listOf(
            findViewById(R.id.btnConvertLength),
            findViewById(R.id.btnConvertWeight),
            findViewById(R.id.btnConvertVolume),
            findViewById(R.id.btnConvertArea),
            findViewById(R.id.btnConvertTemperature),
            findViewById(R.id.btnConvertSpeed),
            findViewById(R.id.btnConvertTime)
        )
    }

    private fun setupSpinners() {
        val lengthUnits = arrayOf("Metros", "Centímetros", "Milímetros", "Polegadas", "Pés", "Jardas", "Milhas")
        val weightUnits = arrayOf("Gramas", "Kilogramas", "Toneladas", "Libras", "Onças")
        val volumeUnits = arrayOf("Litros", "Mililitros", "Galões")
        val areaUnits = arrayOf("Metros Quadrados", "Centímetros Quadrados", "Hectares", "Acres")
        val temperatureUnits = arrayOf("Celsius", "Fahrenheit")
        val speedUnits = arrayOf("Metros por Segundo", "Kilômetros por Hora", "Milhas por Hora")
        val timeUnits = arrayOf("Segundos", "Minutos", "Horas")

        val allUnits = listOf(lengthUnits, weightUnits, volumeUnits, areaUnits, temperatureUnits, speedUnits, timeUnits)

        for (i in inputSpinners.indices) {
            setupSpinner(inputSpinners[i], outputSpinners[i], allUnits[i])
            // Configura os itens iniciais dos spinners
            inputSpinners[i].setSelection(0) // Item 1 (segundo item)
            outputSpinners[i].setSelection(1) // Item 2 (terceiro item)
        }
    }

    private fun setupSpinner(spinnerInput: Spinner, spinnerOutput: Spinner, units: Array<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInput.adapter = adapter
        spinnerOutput.adapter = adapter
    }

    private fun setupButtonListeners() {
        convertButtons.forEachIndexed { index, button ->
            button.setOnClickListener { performConversion(index) }
        }
    }

    private fun performConversion(index: Int) {
        val inputField = inputFields[index]
        val outputField = outputFields[index]
        val inputUnitSpinner = inputSpinners[index]
        val outputUnitSpinner = outputSpinners[index]

        val inputValue = inputField.text.toString()
        if (inputValue.isNotEmpty()) {
            val value = inputValue.toDoubleOrNull()
            if (value != null) {
                val inputUnit = inputUnitSpinner.selectedItem.toString()
                val outputUnit = outputUnitSpinner.selectedItem.toString()
                val result = when (index) {
                    0 -> convertLength(value, inputUnit, outputUnit)
                    1 -> convertWeight(value, inputUnit, outputUnit)
                    2 -> convertVolume(value, inputUnit, outputUnit)
                    3 -> convertArea(value, inputUnit, outputUnit)
                    4 -> convertTemperature(value, inputUnit, outputUnit)
                    5 -> convertSpeed(value, inputUnit, outputUnit)
                    6 -> convertTime(value, inputUnit, outputUnit)
                    else -> 0.0
                }
                outputField.text = "Resultado: $result $outputUnit"
            } else {
                outputField.text = "Valor inválido"
            }
        } else {
            outputField.text = ""
        }
    }

    // Funções de conversão
    private fun convertLength(value: Double, inputUnit: String, outputUnit: String): Double {
        val meters = when (inputUnit) {
            "Metros" -> value
            "Centímetros" -> value / 100
            "Milímetros" -> value / 1000
            "Polegadas" -> value * 0.0254
            "Pés" -> value * 0.3048
            "Jardas" -> value * 0.9144
            "Milhas" -> value * 1609.34
            else -> value
        }

        return when (outputUnit) {
            "Metros" -> meters
            "Centímetros" -> meters * 100
            "Milímetros" -> meters * 1000
            "Polegadas" -> meters / 0.0254
            "Pés" -> meters / 0.3048
            "Jardas" -> meters / 0.9144
            "Milhas" -> meters / 1609.34
            else -> meters
        }
    }

    private fun convertWeight(value: Double, inputUnit: String, outputUnit: String): Double {
        val kilograms = when (inputUnit) {
            "Gramas" -> value / 1000
            "Kilogramas" -> value
            "Toneladas" -> value * 1000
            "Libras" -> value * 0.453592
            "Onças" -> value * 0.0283495
            else -> value
        }

        return when (outputUnit) {
            "Gramas" -> kilograms * 1000
            "Kilogramas" -> kilograms
            "Toneladas" -> kilograms / 1000
            "Libras" -> kilograms / 0.453592
            "Onças" -> kilograms / 0.0283495
            else -> kilograms
        }
    }

    private fun convertVolume(value: Double, inputUnit: String, outputUnit: String): Double {
        val liters = when (inputUnit) {
            "Litros" -> value
            "Mililitros" -> value / 1000
            "Galões" -> value * 3.78541
            else -> value
        }

        return when (outputUnit) {
            "Litros" -> liters
            "Mililitros" -> liters * 1000
            "Galões" -> liters / 3.78541
            else -> liters
        }
    }

    private fun convertArea(value: Double, inputUnit: String, outputUnit: String): Double {
        val squareMeters = when (inputUnit) {
            "Metros Quadrados" -> value
            "Centímetros Quadrados" -> value / 10000
            "Hectares" -> value * 10000
            "Acres" -> value * 4046.86
            else -> value
        }

        return when (outputUnit) {
            "Metros Quadrados" -> squareMeters
            "Centímetros Quadrados" -> squareMeters * 10000
            "Hectares" -> squareMeters / 10000
            "Acres" -> squareMeters / 4046.86
            else -> squareMeters
        }
    }

    private fun convertTemperature(value: Double, inputUnit: String, outputUnit: String): Double {
        return when {
            inputUnit == "Celsius" && outputUnit == "Fahrenheit" -> value * 9 / 5 + 32
            inputUnit == "Fahrenheit" && outputUnit == "Celsius" -> (value - 32) * 5 / 9
            else -> value // Se as unidades forem iguais
        }
    }

    private fun convertSpeed(value: Double, inputUnit: String, outputUnit: String): Double {
        val metersPerSecond = when (inputUnit) {
            "Metros por Segundo" -> value
            "Kilômetros por Hora" -> value / 3.6
            "Milhas por Hora" -> value * 0.44704
            else -> value
        }

        return when (outputUnit) {
            "Metros por Segundo" -> metersPerSecond
            "Kilômetros por Hora" -> metersPerSecond * 3.6
            "Milhas por Hora" -> metersPerSecond / 0.44704
            else -> metersPerSecond
        }
    }

    private fun convertTime(value: Double, inputUnit: String, outputUnit: String): Double {
        val seconds = when (inputUnit) {
            "Segundos" -> value
            "Minutos" -> value * 60
            "Horas" -> value * 3600
            else -> value
        }

        return when (outputUnit) {
            "Segundos" -> seconds
            "Minutos" -> seconds / 60
            "Horas" -> seconds / 3600
            else -> seconds
        }
    }
}
