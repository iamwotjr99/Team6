import Button from '@/components/common/Button';
import { useRouter } from 'next/navigation';
import { useModalStore } from '@/stores/Modal';
import { useRoomStore } from '@/stores/Room'; 
import { createChat } from '@/app/api/chat';

export default function ChatCreateCheckModal() {
  const router = useRouter();
  const setModal = useModalStore.use.setModal();
  const roomName = useRoomStore((state) => state.roomName); 

  const handleClose = () => {
    setModal(null);
    router.push('/chat/create');
  };

  const handleNewChatRoom = async () => {
    try {
      const data = await createChat(roomName);
      console.log('[채팅방 생성 성공]:', data);
      setModal(null);
      router.push(`/chat/${data.id}?title=${encodeURIComponent(data.title)}`);
    } catch (error) {
      console.error(error);
      alert('채팅방 생성에 실패했습니다.');
    }
  };

  return (
    <div className="flex flex-col gap-5 px-4 py-6 items-center justify-center">
      <div className="text-center">
        <div className="text-lg font-bold leading-[29px] text-[#171717]">
          [{roomName}] 채팅방을 생성하시겠습니까?
        </div>
      </div>
      <div className="flex gap-4 font-bold w-60 items-center">
        <Button
          className="bg-white border text-black h-12"
          onClick={handleNewChatRoom}
        >
          네
        </Button>
        <Button className="h-12" onClick={handleClose}>
          아니요
        </Button>
      </div>
    </div>
  );
}