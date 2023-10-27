package com.example.foodrecipe.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.example.foodrecipe.R
import com.example.foodrecipe.models.Result
import com.example.foodrecipe.util.Constants.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private lateinit var mainImage : ImageView
    private lateinit var titleText : TextView
    private lateinit var likesText : TextView
    private lateinit var timeText  : TextView
    private lateinit var summaryText : TextView
    private lateinit var vegetarianImage : ImageView
    private lateinit var vegetarianText : TextView
    private lateinit var veganImage : ImageView
    private lateinit var veganText : TextView
    private lateinit var gluteenfreeImage : ImageView
    private lateinit var gluteenfreeText : TextView
    private lateinit var dairyfreeImage : ImageView
    private lateinit var dairyfreeText : TextView
    private lateinit var healtyImage : ImageView
    private lateinit var healtyText : TextView
    private lateinit var cheapImage : ImageView
    private lateinit var cheapText : TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        mainImage = view.findViewById(R.id.main_imageView)
        titleText = view.findViewById(R.id.title_textView)
        likesText = view.findViewById(R.id.likes_textView)
        timeText = view.findViewById(R.id.time_textView)
        summaryText = view.findViewById(R.id.summary_textView)
        vegetarianImage = view.findViewById(R.id.vegetarian_imageView)
        vegetarianText =view.findViewById(R.id.vegetarian_textView)
        veganImage  =  view.findViewById(R.id.vegan_imageView)
        veganText = view.findViewById(R.id.vegan_textView)
        gluteenfreeImage = view.findViewById(R.id.gluten_free_imageView)
        gluteenfreeText = view.findViewById(R.id.gluten_free_textView)
        dairyfreeImage = view.findViewById(R.id.dairy_free_imageView)
        dairyfreeText = view.findViewById(R.id.dairy_free_textView)
        healtyImage = view.findViewById(R.id.healthy_imageView)
        healtyText = view.findViewById(R.id.healthy_textView)
        cheapImage = view.findViewById(R.id.cheap_imageView)
        cheapText = view.findViewById(R.id.cheap_textView)


        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)


        mainImage.load(myBundle?.image)
        titleText.text = myBundle?.title
        likesText.text= myBundle?.aggregateLikes.toString()
        timeText.text = myBundle?.readyInMinutes.toString()
        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            summaryText.text = summary
        }

        if(myBundle?.vegetarian == true){
            vegetarianImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            vegetarianText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.vegan == true){
            veganImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            veganText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.glutenFree == true){
            gluteenfreeImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            gluteenfreeText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.dairyFree == true){
            dairyfreeImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            dairyfreeText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.veryHealthy == true){
            healtyImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            healtyText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.cheap == true){
            cheapImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            cheapText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }


        return view

    }

}