import { create } from 'zustand';
import createSelectors from '.';

type ModalState = {
  isModalOpen: boolean;
  contents: React.ReactNode | null;
  setModal: (contents: React.ReactNode | null) => void;

  selectedChat?: { id: string; title: string };
  setSelectedChat: (chat: { id: string; title: string } | null) => void;
};

const modalStore = create<ModalState>()((set) => ({
  isModalOpen: false,
  contents: null,
  setModal: (contents) =>
    set(() => ({ isModalOpen: !!contents, contents })),

  selectedChat: undefined,
  setSelectedChat: (chat) =>
    set(() => ({ selectedChat: chat || undefined })),
}));

export const useModalStore = createSelectors(modalStore);