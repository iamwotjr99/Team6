
import { create } from 'zustand';
import createSelectors from '.';

type AgreeState = {
  agreeAge: boolean;
  agreePrivacy: boolean;
  agreeTou: boolean;
  toggleAge: () => void;
  togglePrivacy: () => void;
  toggleTou: () => void;
};

const agreeStore = create<AgreeState>()((set) => ({
  agreeAge: false,
  agreePrivacy: false,
  agreeTou: false,
  toggleAge: () =>
    set((state) => ({ agreeAge: !state.agreeAge })),
  togglePrivacy: () =>
    set((state) => ({ agreePrivacy: !state.agreePrivacy })),
  toggleTou: () =>
    set((state) => ({ agreeTou: !state.agreeTou })),
}));

export const useAgreeStore = createSelectors(agreeStore);
