import { useNavigate, useLocation } from 'react-router-dom';

export default function BottomNav() {
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <nav className="fixed bottom-0 w-full flex justify-around bg-white dark:bg-gray-800 border-t border-gray-300 dark:border-gray-700 p-2">
      <button
        className={`flex-1 py-2 ${location.pathname === '/public' ? 'font-bold' : ''}`}
        onClick={() => navigate('/public')}
      >
        Public
      </button>
      <button
        className={`flex-1 py-2 ${location.pathname === '/private' ? 'font-bold' : ''}`}
        onClick={() => navigate('/private')}
      >
        Private
      </button>
    </nav>
  );
}