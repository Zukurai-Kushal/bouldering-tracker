import { useContext } from 'react';
import { ThemeContext } from '../../context/ThemeContext';
import { AuthContext } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { logout } from '../../api/userApi';

export default function SettingsDropdown() {
  const { darkMode, setDarkMode } = useContext(ThemeContext);
  const { user, setUser } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    setUser(null);
    navigate('/private'); // Redirect to login page
  };

  return (
    <div className="absolute right-0 mt-2 bg-white dark:bg-gray-700 shadow rounded-md p-2 w-40">
      <button
        onClick={() => setDarkMode(!darkMode)}
        className="block w-full text-left px-2 py-1 hover:bg-gray-100 dark:hover:bg-gray-600"
      >
        {darkMode ? 'Light Mode' : 'Dark Mode'}
      </button>
      {!user ? (
        <button
          onClick={() => navigate('/private')}
          className="block w-full text-left px-2 py-1 hover:bg-gray-100 dark:hover:bg-gray-600"
        >
          Login
        </button>
      ) : (
        <button
          onClick={handleLogout}
          className="block w-full text-left px-2 py-1 hover:bg-gray-100 dark:hover:bg-gray-600 text-red-500"
        >
          Logout
        </button>
      )}
    </div>
  );
}