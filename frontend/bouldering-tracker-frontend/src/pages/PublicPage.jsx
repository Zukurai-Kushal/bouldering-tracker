import { useContext, useEffect, useState } from 'react';
import { getSharedClimbs, searchSharedClimbs } from '../api/publicApi';
import ClimbCard from '../components/climbs/ClimbCard';
import FilterModal from '../components/filters/FilterModal';
import SettingsDropdown from '../components/common/SettingsDropdown';
import HeaderBar from '../components/common/HeaderBar';
import { FilterContext } from '../context/FilterContext';
import { useNavigate } from 'react-router-dom';

export default function PublicPage() {
  const { filters, setFilters, getCleanFilters } = useContext(FilterContext);
  const [climbs, setClimbs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [showFilterModal, setShowFilterModal] = useState(false);
  const [showSettings, setShowSettings] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true);
    const fetchData = async () => {
      try {
        const params = getCleanFilters();
        const res = Object.keys(params).length
          ? await searchSharedClimbs(params)
          : await getSharedClimbs();
        setClimbs(res.data);
      } catch {
        setError('Failed to load shared climbs. Please try again.');
        setClimbs([]);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [filters]);

  return (
    <div className="p-4">   
      <HeaderBar
        filters={filters}
        onFilterClick={() => setShowFilterModal(true)}
        onSettingsClick={() => setShowSettings(!showSettings)}
        showSettings={showSettings}
        children={{ onAddClimb: () => navigate('/private', { state: { openAddClimb: true } }) }}
      />
      {showSettings && <SettingsDropdown />}

      {loading && <div>Loading climbs...</div>}
      {error && !loading && <div className="text-red-500">{error}</div>}
      {!loading && !error && climbs.length === 0 && <div>No climbs found. Try adjusting filters.</div>}

      {!loading && !error && climbs.length > 0 && (
        <div className="flex flex-col items-center gap-6 w-full px-4">
          {climbs.map(climb => (
            <ClimbCard key={climb.climbId} climb={climb} isPrivate={false} />
          ))}
        </div>
      )}

      {showFilterModal && (
        <FilterModal
          onClose={() => setShowFilterModal(false)}
          onApply={newFilters => setFilters(newFilters)}
          currentFilters={filters}
        />
      )}
    </div>
  );
}