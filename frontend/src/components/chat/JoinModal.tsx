'use client';
import { useRouter } from 'next/navigation';
import Button from '@/components/common/Button';
import { useModalStore } from '@/stores/Modal';

export default function JoinModal() {
  const router = useRouter();
  const setModal = useModalStore.use.setModal();
  const selectedChat = useModalStore.use.selectedChat?.();

  const handleClose = () => {
    setModal(null);
  };

  const handleJoinChatRoom = () => {
    if (!selectedChat) return;
    setModal(null);
    router.push(`/chat/${selectedChat.id}?title=${encodeURIComponent(selectedChat.title)}`);
  };

  return (
    <div className="flex flex-col gap-5 px-4 py-6 items-center justify-center">
      <div className="text-center">
        <div className="text-lg font-bold leading-[29px] text-[#171717]">
          [{selectedChat?.title}] 채팅방에 들어가시겠습니까?
        </div>
      </div>
      <div className="flex gap-4 font-bold w-60 items-center">
        <Button
          className="bg-white border text-black h-12"
          onClick={handleJoinChatRoom}
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