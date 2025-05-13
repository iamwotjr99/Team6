import { create } from 'zustand';

type RoomState = {
  roomName: string;
  setRoomName: (name: string) => void;
}

export const useRoomStore = create<RoomState>((set) => ({
  roomName: '',
  setRoomName: (name: string) => set({ roomName: name }),
}));