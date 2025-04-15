'use client';

import {useRouter} from 'next/navigation';
import {RiRobot2Line} from 'react-icons/ri';
import CircleButton from '@/components/common/CircleButton';

type Props = {
  botId?: string;
};

export default function ChatBotButton({ botId }: Props) {
  const router = useRouter();

  const handleClick = () => {
    router.push(`/Bot/${botId}`);
  };

  return (
    <CircleButton size="lg" onClick={handleClick}>
      <RiRobot2Line size={27} />
    </CircleButton>
  );
}