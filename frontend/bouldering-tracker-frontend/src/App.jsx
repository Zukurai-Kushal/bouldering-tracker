import AuthProvider from './context/AuthContext';
import ThemeProvider from './context/ThemeContext';
import FilterProvider from './context/FilterContext';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import PublicPage from './pages/PublicPage';
import PrivatePage from './pages/PrivatePage';
import BottomNav from './components/common/BottomNav';

function App() {
  return (
    <AuthProvider>
      <ThemeProvider>
        <FilterProvider>
          <Router>
            <div className="min-h-screen bg-white dark:bg-gray-900 text-gray-900 dark:text-gray-100">
              <Routes>
                <Route path="/public" element={<PublicPage />} />
                <Route path="/private" element={<PrivatePage />} />
                <Route path="*" element={<Navigate to="/public" />} />
              </Routes>
              <BottomNav />
            </div>
          </Router>
        </FilterProvider>
      </ThemeProvider>
    </AuthProvider>
  );
}

export default App;