import AuthProvider from './context/AuthContext';
import ThemeProvider from './context/ThemeContext';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import PublicPage from './pages/PublicPage';
import PrivatePage from './pages/PrivatePage';
import BottomNav from './components/common/BottomNav';

function App() {
  return (
    <ThemeProvider>
      <AuthProvider>
        <Router>
          <div className="min-h-screen bg-gray-100 dark:bg-gray-900 text-gray-900 dark:text-gray-100">
            <Routes>
              <Route path="/public" element={<PublicPage />} />
              <Route path="/private" element={<PrivatePage />} />
              <Route path="*" element={<Navigate to="/public" />} />
            </Routes>
            <BottomNav />
          </div>
        </Router>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;