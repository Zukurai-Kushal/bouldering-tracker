import { createContext, useState } from 'react';

export const FilterContext = createContext();

export default function FilterProvider({ children }) {
  const [filters, setFilters] = useState({});

  const getCleanFilters = () =>
    Object.fromEntries(
      Object.entries(filters).filter(([_, value]) => value !== null && value !== '')
    );

  return (
    <FilterContext.Provider value={{ filters, setFilters , getCleanFilters}}>
      {children}
    </FilterContext.Provider>
  );
}