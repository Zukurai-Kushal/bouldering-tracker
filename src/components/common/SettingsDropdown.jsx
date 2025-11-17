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
    navigate('/private');
  };

  return (
    <div className="absolute top-20 right-0 mt-2 w-40 bg-gray-800 p-2 space-y-2 rounded-md shadow-lg">
      <button
        onClick={() => setDarkMode(!darkMode)}
        className="block w-full text-left px-2 py-1 text-white hover:bg-blue-600"
      >
        {darkMode ? 'Light Mode' : 'Dark Mode'}
      </button>
      {!user ? (
        <button
          onClick={() => navigate('/private')}
          className="block w-full text-left px-2 py-1 text-white hover:bg-blue-600"
        >
          Login
        </button>
      ) : (
        <button
          onClick={handleLogout}
          className="block w-full text-left px-2 py-1 text-white hover:bg-blue-600"
        >
          Logout
        </button>
      )}
    </div>
  );
}