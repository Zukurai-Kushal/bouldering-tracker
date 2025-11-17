import { useState, useContext } from 'react';
import { login, getCurrentUser } from '../../api/userApi';
import { AuthContext } from '../../context/AuthContext';

export default function LoginForm({ onSwitchToRegister }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState('');
  const [loading, setLoading] = useState(false);
  const { setUser } = useContext(AuthContext);

  const validate = () => {
    const newErrors = {};
    if (!username || username.length < 3) {
      newErrors.username = 'Username must be at least 3 characters.';
    }
    if (!password || password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters.';
    }
    return newErrors;
  };

  const handleLogin = async (e) => {
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
      await login(username, password);
      const res = await getCurrentUser();
      setUser(res.data); // This triggers PrivatePage to show climbs
    } catch {
      setApiError('Invalid username or password.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto p-4 border rounded shadow-md dark:bg-gray-800">
      <h3 className="text-xl font-bold mb-4">Login</h3>
      {apiError && <div className="text-red-500 mb-2">{apiError}</div>}
      <form onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="w-full mb-1 border rounded px-2 py-1 dark:bg-gray-700"
        />
        {errors.username && <div className="text-red-500 text-sm mb-2">{errors.username}</div>}

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
          {loading ? 'Logging in...' : 'Login'}
        </button>
      </form>
      <p className="mt-4 text-center">
        Don’t have an account?{' '}
        <span
          onClick={onSwitchToRegister}
          className="text-blue-500 cursor-pointer hover:underline"
        >
          Register here
        </span>
      </p>
    </div>
  );
}