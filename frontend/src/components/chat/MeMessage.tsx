'use client';

type Props = {
  message: string;
  time: string;
};

export default function MeMessage({ message, time }: Props) {
  return (
    <div className="mb-2 flex flex-col items-end">
      <div className="rounded-xl bg-green-600 px-4 py-2 text-sm text-white max-w-[80%]">
        {message}
      </div>
      <span className="text-[12px] text-gray-400 pr-1">{time}</span>
    </div>
  );
}