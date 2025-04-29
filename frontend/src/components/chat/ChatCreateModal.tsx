import Button from '@/components/common/Button';
import { useRouter } from 'next/navigation';
import {useModalStore} from '@/stores/Modal';

export default function ChatCreateModal() {
  const router = useRouter();
  const setModal = useModalStore.use.setModal();

  const handleClose = () => {
    setModal(null);
    router.push('/chat');
  }

  const handleNewChat = () => {
    setModal(null);
    router.push('/chat/create');
  }

  return(
    <div className="flex flex-col gap-5 px-4 py-6 items-center justify-center">
      <div>
        <div className="flex text-lg font-bold leading-[29px] text-[#171717]">
          <p>새로운 채팅방을 만드시겠습니까?</p>
        </div>
      </div>
      <div className="flex gap-4 font-bold w-60 items-center">
        <Button
          className="bg-white border text-black h-12"
          onClick={handleNewChat}
        >
          네
        </Button>
        <Button className='h-12' onClick={handleClose}>아니요</Button>
      </div>
    </div>
  )
}