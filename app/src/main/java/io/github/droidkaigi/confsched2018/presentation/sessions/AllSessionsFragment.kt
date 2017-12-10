package io.github.droidkaigi.confsched2018.presentation.sessions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.droidkaigi.confsched2018.databinding.FragmentAllSessionsBinding
import io.github.droidkaigi.confsched2018.di.Injectable
import io.github.droidkaigi.confsched2018.presentation.Result
import javax.inject.Inject

class AllSessionsFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentAllSessionsBinding
    private lateinit var adapter: SessionsAdapter
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val sessionsViewModel: AllSessionsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AllSessionsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAllSessionsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = SessionsAdapter()
        binding.sessionsRecycler.adapter = adapter

        sessionsViewModel.sessions.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> {
                    adapter.sessions = result.data
                }
            }
        })
        lifecycle.addObserver(sessionsViewModel)
    }

    companion object {
        fun newInstance(): AllSessionsFragment = AllSessionsFragment()
    }
}
