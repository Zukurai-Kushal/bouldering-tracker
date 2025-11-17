import { useState, useContext } from 'react';
import { login, getCurrentUser } from '../../api/userApi';
import { AuthContext } from '../../context/AuthContext';

export default function LoginForm() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const { setUser } = useContext(AuthContext);

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      await login(username, password);
      const res = await getCurrentUser();
      setUser(res.data);
    } catch {
      setError('Invalid username or password.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleLogin} className="p-4 bg-white dark:bg-gray-800 rounded shadow max-w-sm mx-auto">
      <h2 className="text-lg font-bold mb-4">Login</h2>

      {error && <p className="text-red-500 mb-2">{error}</p>}

      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        className="w-full mb-2 border rounded px-2 py-1 dark:bg-gray-700"
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className="w-full mb-4 border rounded px-2 py-1 dark:bg-gray-700"
      />

      <button
        type="submit"
        disabled={loading}
        className={`w-full px-3 py-2 rounded text-white ${loading ? 'bg-gray-400' : 'bg-blue-500 hover:bg-blue-600'}`}
      >
        {loading ? 'Logging in...' : 'Login'}
      </button>
    </form>
  );
}