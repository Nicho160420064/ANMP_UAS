package com.ubaya.uas_anmp.view


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.ubaya.uas_anmp.databinding.FragmentAddReservationBinding
import com.ubaya.uas_anmp.model.Reservation
import com.ubaya.uas_anmp.util.UbayaKulinerWorker
import com.ubaya.uas_anmp.util.loadImage
import com.ubaya.uas_anmp.viewmodel.DetailViewModel
import com.ubaya.uas_anmp.viewmodel.ListViewModel
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class AddReservationFragment : Fragment(),ButtonAddNewReservationClickListener, DateTimeClickListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    private lateinit var dataBinding: FragmentAddReservationBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var viewModelList: ListViewModel
    var tenants : Array<String> = arrayOf()
    var tenantName= mutableListOf<String>()
    var selectedItemString = ""
    var year= 0
    var month= 0
    var day= 0
    var hour= 0
    var minute= 0

    var accountId:String?=""

    companion object{
        val SHARED_ACCOUNT_ID="SHARED_ACCOUNT_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentAddReservationBinding.inflate(inflater, container, false)
        return dataBinding.root}

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {super.onViewCreated(view, savedInstanceState)
        var photoUrl = "https://iccadubai.ae/stockpot/wp-content/uploads/sites/2/2020/06/Culinary-Insights-The-UAE-Food.jpg"
        dataBinding.imageViewImageReservation.loadImage(photoUrl, dataBinding.progressLoadImageAddReservation)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetchAccount("anna_")


        dataBinding.datetimeListener = this
        dataBinding.listener = this

        //Get Id
        var sharedId = context?.packageName
        var shared = context?.getSharedPreferences(sharedId, Context.MODE_PRIVATE)
        accountId = shared?.getString(AccountFragment.SHARED_ACCOUNT_ID, null)

        // Instantiate
        dataBinding.reservation = Reservation(selectedItemString,"","","","","","Waiting", accountId)

        viewModelList= ViewModelProvider(this).get(ListViewModel::class.java)
        viewLifecycleOwner.lifecycleScope.launch {
            var tenants= viewModelList.getTenant()
            for (data in tenants){
                tenantName.add(data.name.toString())
            }
            val spinner = dataBinding.spinnerTenant
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tenantName)
            adapter.setDropDownViewResource(com.ubaya.uas_anmp.R.layout.myspinner_item_layout)
            spinner.adapter = adapter
        }
        dataBinding.spinnerTenant.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                selectedItemString = parent.getItemAtPosition(pos) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    override fun onButtonAddNewReservationClick(v: View) {
        val calendar= Calendar.getInstance()
        calendar.set(year, month, day, hour, minute, 0)

        val today= Calendar.getInstance()
        val diff= calendar.timeInMillis/ 1000L - today.timeInMillis/ 1000L

        var totalReservasi=0

        // Do Add Reservation here
        dataBinding.reservation?.let {
            it.tenantName=selectedItemString
            val list = listOf(it)
            viewModel.addReservation(list)
            Toast.makeText(v.context, "Your reservation has been successfully added!", Toast.LENGTH_SHORT).show()

            val myWorkReq = OneTimeWorkRequestBuilder<UbayaKulinerWorker>()
                .setInitialDelay(diff, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "title" to "Reminder Notification",
                        "message" to "It is now the day before your reservation date",
                        "description" to "Dear, ${it.reservationName}\nWe would like to remind you of your reservation.\nThese are your reservation details:\n" +
                                "Tenant name: ${it.tenantName}\nDate & Time: ${it.date} ${it.time}\n" +
                                "Number of person: ${it.people}\n\nSee you soon!")
                )
                .build()
            WorkManager.getInstance(requireContext()).enqueue(myWorkReq)
            Navigation.findNavController(v).popBackStack()
        }
    }

    override fun onDateClick(v: View) {
        val calendar= Calendar.getInstance()
        calendar.add(Calendar.DATE, +0)
        val year= calendar.get(Calendar.YEAR)
        val month= calendar.get(Calendar.MONTH)
        val day= calendar.get(Calendar.DAY_OF_MONTH)
        activity?.let {
            val datePickerDialog = DatePickerDialog(it, this, year, month, day)
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }
    }

    override fun onTimeClick(v: View) {
        val calendar= Calendar.getInstance()
        val hour= calendar.get(Calendar.HOUR_OF_DAY)
        val minute= calendar.get(Calendar.MINUTE)
        TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity)).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Calendar.getInstance().let {
            it.set(year, month, day)
            val date= day.toString().padStart(2, '0')+"-"+
                    month.toString().padStart(2, '0')+"-"+
                    year.toString()
            dataBinding.editTextDate.setText(date)
            this.year= year
            this.month= month
            this.day= day
        }
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        val time= hourOfDay.toString().padStart(2, '0')+':'+
                minute.toString().padStart(2, '0')
        dataBinding.editTextTime.setText(time)
        this.hour= hourOfDay
        this.minute=minute
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var selected= tenants[p2]
        selectedItemString =selected
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}