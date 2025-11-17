import { useNavigate, useLocation } from 'react-router-dom';

export default function BottomNav() {
  const navigate = useNavigate();
  const location = useLocation();

  const getButtonClasses = (path) =>
    `flex-1 py-2 ${
      location.pathname === path
        ? 'bg-blue-600 text-green-400 font-bold'
        : 'bg-gray-800 text-white'
    } hover:bg-blue-500 transition`;

  return (
    <nav className="fixed bottom-0 w-full flex justify-around bg-gray-800 p-2 space-x-2">
      <button
        className={getButtonClasses('/public')}
        onClick={() => navigate('/public')}
      >
        Public
      </button>
      <button
        className={getButtonClasses('/private')}
        onClick={() => navigate('/private')}
      >
        Private
      </button>
    </nav>
  );
}