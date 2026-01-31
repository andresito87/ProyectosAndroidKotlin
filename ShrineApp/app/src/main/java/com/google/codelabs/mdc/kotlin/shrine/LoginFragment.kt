package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.google.codelabs.mdc.kotlin.shrine.databinding.ShrLoginFragmentBinding

class LoginFragment() : Fragment() {

    private var _binding: ShrLoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val navigationHost: NavigationHost
        get() = requireActivity() as NavigationHost

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShrLoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Validar al pulsar Next
        binding.nextButton.setOnClickListener {
            if (!isPasswordValid(binding.passwordEditText.text)) {
                binding.passwordTextInput.error = getString(R.string.shr_error_password)
            } else {
                binding.passwordTextInput.error = null
                navigationHost.navigateTo(ProductGridFragment(), false)
            }
        }

        // Limpiar error cuando el password sea válido (mejor que OnKeyListener)
        binding.passwordEditText.doAfterTextChanged {
            if (isPasswordValid(it)) {
                binding.passwordTextInput.error = null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }
}
