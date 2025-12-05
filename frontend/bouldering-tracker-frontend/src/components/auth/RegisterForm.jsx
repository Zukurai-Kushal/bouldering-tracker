import { useState, useContext } from 'react';
import { register, login, getCurrentUser } from '../../api/userApi';
import { AuthContext } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function RegisterForm({ onSwitchToLogin }) {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState('');
  const [loading, setLoading] = useState(false);
  const { setUser } = useContext(AuthContext);
  const navigate = useNavigate();

  const validate = () => {
    const newErrors = {};
    if (!username || username.length < 3 || username.length > 20) {
      newErrors.username = 'Username must be 3-20 characters.';
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!email || !emailRegex.test(email)) {
      newErrors.email = 'Please enter a valid email.';
    }
    if (!password || password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters.';
    }
    return newErrors;
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setErrors({});
    setApiError('');
    const validationErrors = validate();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    setLoading(true);
    try {
      await register(username, email, password);
      await login(username, password);
      const res = await getCurrentUser();
      setUser(res.data);
      navigate('/private');
    } catch (err) {
      const message = err.response?.data?.message || 'Unknown error';
      setApiError(`Registration failed: ${message}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto p-4 border rounded shadow-md dark:bg-gray-800">
      <h3 className="text-xl font-bold mb-4">Register</h3>
      {apiError && <div className="text-red-500 mb-2">{apiError}</div>}
      <form onSubmit={handleRegister}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="w-full mb-1 border rounded px-2 py-1 dark:bg-gray-700"
        />
        {errors.username && <div className="text-red-500 text-sm mb-2">{errors.username}</div>}

        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full mb-1 border rounded px-2 py-1 dark:bg-gray-700"
        />
        {errors.email && <div className="text-red-500 text-sm mb-2">{errors.email}</div>}

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full mb-1 border rounded px-2 py-1 dark:bg-gray-700"
        />
        {errors.password && <div className="text-red-500 text-sm mb-4">{errors.password}</div>}

        <button
          type="submit"
          className="w-full bg-blue-500 text-white py-2 rounded"
          disabled={loading}
        >
          {loading ? 'Registering...' : 'Register'}
        </button>
      </form>
      <p className="mt-4 text-center">
        Already have an account?{' '}
        <span
          onClick={onSwitchToLogin}
          className="text-blue-500 cursor-pointer hover:underline"
        >
          Login here
        </span>
      </p>
    </div>
  );
}